######场景：
文本上下自动滚动切换。

######问题：
同事用ViewFlipper频繁的删除view,添加view会导致文字重叠的情况。

######为什么会出现这种问题：
如果频繁的动态给ViewFlipper添加子View的话，可能刚好在ViewFlipper执行切换上下View动画的时候，刚好加进导致两个View重叠

######解决方式：
使用固定两个View执行上下切换的动画，变化的只是文本即可，那么能实现我们这种方案的是TextSwitcher控件

[TextSwitcher控件](https://developer.android.com/reference/android/widget/TextSwitcher)

[自己做的Demo,模仿多线程频繁clear数据，增加数据](https://github.com/zhongjhATC/TextSwitcherDemo)

######其他
因为是继承于ViewSwitch控件，所以可以对view进行一些动态代码修改动作，比如下面获取到NextView控件
```
            int currentIndex = index % mShufflings.size();
            if (mTemperatureResultTipsPosition != -1 && currentIndex == mTemperatureResultTipsPosition) {
                // 设置背景绿色
                WristMainResultViewController.this.viewHolder.mainBottomTextSwitcher.getNextView().setBackground(this.context.getDrawable(R.drawable.wrist_bottom_right_blue_bg));
            } else if (this.statusResultSet.getPassCheckStatus() == PassCheckStatus.prohibit) {
                WristMainResultViewController.this.viewHolder.mainBottomTextSwitcher.getNextView().setBackground(this.context.getDrawable(R.drawable.wrist_bottom_right_red_bg));
            }
            WristMainResultViewController.this.viewHolder.mainBottomTextSwitcher.setText(mShufflings.get(currentIndex));
```



