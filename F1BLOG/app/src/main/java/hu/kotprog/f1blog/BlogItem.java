package hu.kotprog.f1blog;

public class BlogItem {
    private String title;
    private String longerText;
    private int imageResource;



    public BlogItem(){}


    public BlogItem(String title, String longerText, int imageResource) {
        this.title = title;
        this.longerText = longerText;
        this.imageResource = imageResource;
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

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
