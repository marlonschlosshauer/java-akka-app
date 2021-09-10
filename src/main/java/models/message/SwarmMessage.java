package models.message;

public interface SwarmMessage {
    public class SwarmEcho implements SwarmMessage {
        public String echo;
        public int amount;

        public SwarmEcho(String echo, int amount) {
            this.echo = echo;
            this.amount = amount;
        }
    }

    public class SwarmResponse implements SwarmMessage {
        public String response;

        public SwarmResponse(String response) {
            this.response = response;
        }
    }
}
