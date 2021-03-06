package com.company;


import javax.swing.*;

import java.awt.*;

import static java.lang.Thread.sleep;

public class JumpChess extends JLabel
{
    private int state=1;//棋子存活状态，其中1为存活，2为死亡
    public JumpChess(Icon image)
    {
        //棋子初始化在左侧
        this(null, image);
        setBounds(0, 150, image.getIconWidth(), image.getIconHeight());
    }

    public JumpChess(String text, Icon icon)
    {
        setText(text);
        setIcon(icon);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }

    /**
     * 跳跃函数，对于跳跃函数，我们
     *
     * @param MouseTime 从鼠标获取的时间
     * @param plat 平台参数，方便确定碰撞
     */
    public void jump(double MouseTime,Plat plat)
    {
        //起跳的速度有点太快，可以考虑分段--HuaCL20210620 2222
        System.out.println("get it!!");
        //横向速度（匀速运动,初始值和鼠标按压时间成正比）
        double Vx=15+MouseTime*0.02;
        //纵向加速度（匀加速运动）
        double Ay=-0.2;
        //纵向速度（初始值和鼠标按压时间成正比）
        double Vy=10+MouseTime*0.06;
        //时间
        int actionTime=0;
        //系数，用于将动画更加精细化，10即为/10显示
        int Multiplayer=50;
        //单位时间，用于控制移动速度
        //double standardGapTime=0.5;
        //初始位置
        int initialX,initialY;
        initialX=super.getX();
        initialY=super.getY();

        //System.out.println(super.getX()+"   "+super.getY());

        int distance = (int) MouseTime / 20;
        //通过不停的刷新棋子的位置实现动画
        while(true)
        {
            setLocation((int)(initialX + (Vx*actionTime)/Multiplayer),
                    (int)(initialY - (Vy*actionTime+0.5*Ay*actionTime*actionTime)/Multiplayer));
            //System.out.println("position:("+super.getX()+","+super.getY()+")");
            //System.out.println("speed:("+Vx+","+Vy+Vy*actionTime+")");

            try
            {
                sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            actionTime+=1;
            //actionTime+=standardGapTime;
            //Judge返回1，表示正确跳到台上，设定状态为1，继续游戏
            if(plat.Judge(this.getX(),this.getWidth(),this.getY(),this.getHeight())==1)
            {
                this.state=1;
                break;
            }
            //Judge返回2，游戏失败
            if(plat.Judge(this.getX(),this.getWidth(),this.getY(),this.getHeight())==2)
            {
                //棋子掉落下去的动画
                do
                {
                    setLocation(super.getX(),super.getY()+1);
                    try
                    {
                        sleep(1);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }while(super.getY()<400);
                this.state=2;
                break;
            }
            //Judge返回3，表示跳到台上,但是重心较远，设定状态为2，游戏失败
            if(plat.Judge(this.getX(),this.getWidth(),this.getY(),this.getHeight())==3)
            {
                //棋子掉落下去的动画
                if (this.getX() < plat.getX())//棋子在平台左侧
                {
                    do
                    {
                        setLocation(super.getX()-1, super.getY() + 1);
                        try
                        {
                            sleep(1);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    } while (this.getX() + this.getWidth() >= plat.getX());
                    do
                    {
                        setLocation(super.getX(),super.getY()+2);
                        try
                        {
                            sleep(1);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }while(super.getY()<400);
                }else{
                    do
                    {
                        setLocation((int)(initialX + (Vx*actionTime)/Multiplayer),
                                (int)(initialY - (Vy*actionTime+0.5*Ay*actionTime*actionTime)/Multiplayer));
                        try
                        {
                            sleep(1);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        actionTime+=1;
                    } while (this.getX()<= plat.getX()+plat.getWidth());
                    do
                    {
                        setLocation(super.getX() , super.getY() + 2);
                        try
                        {
                            sleep(1);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }while(super.getY()<400);
                }

                this.state=2;
                break;
            }
            //棋子飞出游戏区域外都没有出发碰撞，设定状态为2，游戏失败
            if(super.getY()>400)
            {
                this.state=2;
                break;
            }
        }
    //    setLocation(initialX + distance, initialY - distance);
    }
    public int getState()
    {
        return this.state;
    }
}

