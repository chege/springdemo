package springdemo.joke;

class Joke {
    private String joke;
    private String category;

    Joke(String joke, String category) {
        this.joke = joke;
        this.category = category;
    }

    public String getJoke() {
        return joke;
    }

    public String getCategory() {
        return category;
    }
}
