package hu.kotprog.f1blog;

public class BlogItem {
    private String blogKey;
    private String title;
    private String longerText;
    private String image;

    public String getBlogKey() {
        return blogKey;
    }

    public void setBlogKey(String blogKey) {
        this.blogKey = blogKey;
    }

    public BlogItem(){}


    public BlogItem(String title, String longerText, String image) {
        this.title = title;
        this.longerText = longerText;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLongerText() {
        return longerText;
    }

    public void setLongerText(String longerText) {
        this.longerText = longerText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
