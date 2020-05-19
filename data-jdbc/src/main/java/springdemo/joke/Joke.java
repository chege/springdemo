package springdemo.joke;

import java.util.Objects;

class Joke {
    private final String joke;
    private final String category;

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

    @Override
    public String toString() {
        return "Joke{" +
                "joke='" + joke + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joke joke1 = (Joke) o;
        return joke.equals(joke1.joke) &&
                category.equals(joke1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(joke, category);
    }
}
