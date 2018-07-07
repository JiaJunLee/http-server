package pers.burgess.httpserver.core;

import pers.burgess.httpserver.core.model.ResourceNotFound;
import pers.burgess.httpserver.core.res.*;

import java.io.*;

public class ResourceManager {

    public static InputStream getStream(Resource resource) throws FileNotFoundException, ResourceNotFound {

        if (resource instanceof ForwardResource) {
            return new ByteArrayInputStream(((ForwardResource) resource).getUrl().getPath().getBytes());
        } else if (resource instanceof SystemResource) {
            File file = new File(((SystemResource) resource).getUrl().getFile());
            return new FileInputStream(file);
        } else if (resource instanceof StaticResource) {
            File file = new File(((StaticResource) resource).getUrl().getFile());
            return new FileInputStream(file);
        } else if (resource instanceof StringResource) {
            return new ByteArrayInputStream(((StringResource) resource).getResource().getBytes());
        }

        throw new ResourceNotFound("can not get resource");

    }

}
