package com.example.nanchen.aiyaschoolpush.utils.compress;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;


import java.io.File;

import rx.Observable;
import rx.functions.Func0;

/**
 * 压缩方法工具类
 *
 * 该方法目前和上面的包的不一样，具体请查看
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-03-08  9:03
 */

public class Compressor {
    private static volatile Compressor INSTANCE;
    private Context context;
    //max width and height values of the compressed image is taken as 612x816
    private float maxWidth = 612.0f;
    private float maxHeight = 816.0f;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;
    private int quality = 80;
    private String destinationDirectoryPath;
    private String fileNamePrefix;
    private String fileName;


    private Compressor(Context context) {
        this.context = context;
        destinationDirectoryPath = context.getCacheDir().getPath() + File.pathSeparator + FileUtil.FILES_PATH;
    }

    public static Compressor getDefault(Context context) {
        if (INSTANCE == null) {
            synchronized (Compressor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Compressor(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 压缩成文件
     * @param file  原始文件
     * @return      压缩后的文件
     */
    public File compressToFile(File file) {
        return ImageUtil.compressImage(context, Uri.fromFile(file), maxWidth, maxHeight,
                compressFormat, bitmapConfig, quality, destinationDirectoryPath,
                fileNamePrefix, fileName);
    }

    /**
     * 压缩为Bitmap
     * @param file  原始文件
     * @return      压缩后的Bitmap
     */
    public Bitmap compressToBitmap(File file) {
        return ImageUtil.getScaledBitmap(context, Uri.fromFile(file), maxWidth, maxHeight, bitmapConfig);
    }

    public Observable<File> compressToFileAsObservable(final File file) {
        return Observable.defer(new Func0<Observable<File>>() {
            @Override
            public Observable<File> call() {
                return Observable.just(compressToFile(file));
            }
        });
    }

    public Observable<Bitmap> compressToBitmapAsObservable(final File file) {
        return Observable.defer(new Func0<Observable<Bitmap>>() {
            @Override
            public Observable<Bitmap> call() {
                return Observable.just(compressToBitmap(file));
            }
        });
    }

    public static class Builder {
        private Compressor compressor;

        public Builder(Context context) {
            compressor = new Compressor(context);
        }

        public Builder setMaxWidth(float maxWidth) {
            compressor.maxWidth = maxWidth;
            return this;
        }

        public Builder setMaxHeight(float maxHeight) {
            compressor.maxHeight = maxHeight;
            return this;
        }

        public Builder setCompressFormat(Bitmap.CompressFormat compressFormat) {
            compressor.compressFormat = compressFormat;
            return this;
        }

        public Builder setBitmapConfig(Bitmap.Config bitmapConfig) {
            compressor.bitmapConfig = bitmapConfig;
            return this;
        }

        public Builder setQuality(int quality) {
            compressor.quality = quality;
            return this;
        }

        public Builder setDestinationDirectoryPath(String destinationDirectoryPath) {
            compressor.destinationDirectoryPath = destinationDirectoryPath;
            return this;
        }

        public Builder setFileNamePrefix(String prefix) {
            compressor.fileNamePrefix = prefix;
            return this;
        }

        public Builder setFileName(String fileName) {
            compressor.fileName = fileName;
            return this;
        }

        public Compressor build() {
            return compressor;
        }
    }
}
