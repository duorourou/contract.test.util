package duorourou.restful.testing.utils.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Foo {

    public Foo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;
    private List<Foo> fooList;
}
