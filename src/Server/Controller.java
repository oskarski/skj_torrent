package Server;

public interface Controller<RequestType extends Request, ResponseType extends Response> {
    ResponseType handleRequest(RequestType request);
}
