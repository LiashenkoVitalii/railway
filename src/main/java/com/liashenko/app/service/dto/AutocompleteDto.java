package com.liashenko.app.service.dto;

import java.io.Serializable;


//Used to contain data to transfer data with autocompleted words to front-end
public class AutocompleteDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String autocompleteWord;

    public AutocompleteDto() {
    }

    public AutocompleteDto(Long id, String autocompleteWord) {
        this.id = id;
        this.autocompleteWord = autocompleteWord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutocompleteWord() {
        return autocompleteWord;
    }

    public void setAutocompleteWord(String autocompleteWord) {
        this.autocompleteWord = autocompleteWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AutocompleteDto)) return false;

        AutocompleteDto that = (AutocompleteDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return autocompleteWord != null ? autocompleteWord.equals(that.autocompleteWord) : that.autocompleteWord == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (autocompleteWord != null ? autocompleteWord.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AutocompleteDto{");
        sb.append("id=").append(id);
        sb.append(", autocompleteWord='").append(autocompleteWord).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
