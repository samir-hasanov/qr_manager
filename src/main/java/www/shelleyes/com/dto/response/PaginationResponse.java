package www.shelleyes.com.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class PaginationResponse<T> {
    private List<T> content;
    private int totalPages;

    public PaginationResponse(List<T> content, int totalPages) {
        this.content = content;
        this.totalPages = totalPages;
    }

    public PaginationResponse() {

    }
}
