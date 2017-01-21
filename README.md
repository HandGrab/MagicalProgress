# MagicalProgress
一个六边形圆角进度条，可编辑进度条的宽度，
颜色。博客地址（Blog Address）http://www.jianshu.com/p/fad5f4c2a3e8
![](http://upload-images.jianshu.io/upload_images/2159288-ebff48ed4bf1fa61.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
（此处心中无数头草泥马奔腾而过，内心受到成吨的伤害，丑就算了，他竟然还加了一个修饰词），不过此处也要向他致谢，如果不是他的暴击，我也不会在老老实实的把他写一遍。毕竟，我很懒。 

##public API
```
 /**
  * 设置进度
  *
  * @param progress 当前进度
  */
 public void setProgress(float progress) 
 
 /**
   * 设置进度条的宽度
   *
   * @param width 进度条的宽度 dp值
  */
  public void setProgressWith(float width) 

/**
  * 设置中央背景的颜色
  *
  * @param color 中央背景颜色
  */
 public void setCenterColor(int color) 

 /**
  * 设置已完成进度条的颜色
  *
  * @param color 已完成的进度条颜色
 */

 public void setFinishedColor(int color)

 /**
  * 设置未完成进度条的颜色
  *
  * @param color 未完成的进度条的颜色
  */
 public void setUnfinishedColor(int color)

```
