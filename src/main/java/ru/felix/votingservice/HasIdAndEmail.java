package ru.felix.votingservice;

public interface HasIdAndEmail extends HasId {
    String getEmail();
}