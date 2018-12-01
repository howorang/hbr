public interface QueueConfiguration {
    public default String getUrl() {
        return "localhost:8080";
    }
}