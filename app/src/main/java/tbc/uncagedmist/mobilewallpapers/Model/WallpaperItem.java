package tbc.uncagedmist.mobilewallpapers.Model;

public class WallpaperItem {

    public String imageId;
    public String imageUrl;
    public String desc;
    public long viewCount;
    public long downloadCount;

    public WallpaperItem() {
    }

    public WallpaperItem(String imageId, String imageUrl, String desc, long viewCount, long downloadCount) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.desc = desc;
        this.viewCount = viewCount;
        this.downloadCount = downloadCount;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(long downloadCount) {
        this.downloadCount = downloadCount;
    }
}