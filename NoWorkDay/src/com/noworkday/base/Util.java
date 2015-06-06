package com.noworkday.base;

import java.io.File;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.noworkday.R;

public class Util {
	public static String getText(String text) {
		int length = text.length();
		if (length < 13) {
			return text;
		} else if (length > 24) {
			text = text.substring(0, 24);
		}
		String textHeadString = text.substring(0, 13);
		text = text.replace(textHeadString, textHeadString+"\n");
		return text;
	}
	public static SlidingMenu initSlidingMenu(Activity context) {
		SlidingMenu slidingMenu = new SlidingMenu(context);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.attachToActivity(context, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.menu_left);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setShadowWidth(30);
        return slidingMenu;
	}
	public static void initActionButton(Activity context, final SlidingMenu slidingMenu) {
		View view = (View) context.findViewById(R.id.header_bar);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				slidingMenu.showMenu();
			}
		});
	}
	public static DisplayImageOptions getImageOption(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache"); 
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
			.Builder(context)  
		    .memoryCacheExtraOptions(480, 800) // max width, max height���������ÿ�������ļ�����󳤿�  
		    .discCacheExtraOptions(480, 800, null) // Can slow ImageLoader, use it carefully (Better don't use it)/���û������ϸ��Ϣ����ò�Ҫ�������  
		    .threadPoolSize(3)//�̳߳��ڼ��ص�����  
		    .threadPriority(Thread.NORM_PRIORITY - 2)  
		    .denyCacheImageMultipleSizesInMemory()  
		    .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/�����ͨ���Լ����ڴ滺��ʵ��  
		    .memoryCacheSize(2 * 1024 * 1024)    
		    .discCacheSize(50 * 1024 * 1024)    
		    .discCacheFileNameGenerator(new Md5FileNameGenerator())//�������ʱ���URI������MD5 ����  
		    .tasksProcessingOrder(QueueProcessingType.LIFO)  
		    .discCacheFileCount(100) //������ļ�����  
		    .discCache(new UnlimitedDiskCache(cacheDir))//�Զ��建��·��  
		    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())  
		    .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)��ʱʱ��  
		    .writeDebugLogs() // Remove for release app  
		    .build();
		ImageLoader.getInstance().init(config);
		DisplayImageOptions options;
		options = new DisplayImageOptions
				.Builder()  
		 		.showImageOnLoading(R.drawable.ic_launcher) //����ͼƬ�������ڼ���ʾ��ͼƬ  
		 		.showImageForEmptyUri(com.noworkday.R.drawable.ic_launcher)//����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ  
		 		.showImageOnFail(com.noworkday.R.drawable.ic_launcher)  //����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
		 		.cacheInMemory(true)//�������ص�ͼƬ�Ƿ񻺴����ڴ���  
		 		.cacheOnDisc(true)//�������ص�ͼƬ�Ƿ񻺴���SD����  
		 		.considerExifParams(true)  //�Ƿ���JPEGͼ��EXIF��������ת����ת��
		 		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//����ͼƬ����εı��뷽ʽ��ʾ  
		 		.resetViewBeforeLoading(true)//����ͼƬ������ǰ�Ƿ����ã���λ  
				.displayer(new RoundedBitmapDisplayer(20))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
				.displayer(new FadeInBitmapDisplayer(100))//�Ƿ�ͼƬ���غú���Ķ���ʱ��  
				.build();
		return options;
	}
}
