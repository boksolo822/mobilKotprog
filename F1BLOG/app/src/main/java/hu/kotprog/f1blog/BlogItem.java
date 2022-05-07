package hu.kotprog.f1blog;

public class BlogItem {

private String id;
    private String userEmail;
    private String title;
    private String longerText;
    private String image;
    private int clicks;

    public BlogItem() {
    }

    public BlogItem(String title, String longerText, String image) {
        this.title = title;
        this.longerText = longerText;
        this.image = image;
        this.clicks = 0;
    }

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getLongerText() {
        return longerText;
    }

    public String getImage() {
        return image;
    }

    public int getClicks() {
        return clicks;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
