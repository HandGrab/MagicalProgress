# MagicalProgress
一个六边形圆角进度条，可编辑进度条的宽度，颜色。



博客地址（Blog Address）http://www.jianshu.com/p/fad5f4c2a3e8
![效果图](http://upload-images.jianshu.io/upload_images/2159288-004877945ce7f99d.gif?imageMogr2/auto-orient/strip)

####public API
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
