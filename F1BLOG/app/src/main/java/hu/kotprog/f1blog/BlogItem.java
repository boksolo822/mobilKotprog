package hu.kotprog.f1blog;

public class BlogItem {

private String id;
    private String userEmail;
    private String title;
    private String longerText;
    private String image;
    private int clicks;

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }


    public BlogItem() {
    }

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public BlogItem(String title, String longerText, String image) {
        this.title = title;
        this.longerText = longerText;
        this.image = image;
        this.clicks = 0;
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
