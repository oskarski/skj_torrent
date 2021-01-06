package Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Controller<RequestType extends Request, ResponseType extends Response> {
    protected RequestType request;

    public abstract ResponseType handleRequest(RequestType request);

    protected Matcher getDataMatcher(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.request.getData());

        if (!matcher.find()) throw ServerException.badRequestException();

        return matcher;
    }
}
