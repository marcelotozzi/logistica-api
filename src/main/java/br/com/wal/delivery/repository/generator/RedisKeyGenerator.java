package br.com.wal.delivery.repository.generator;

import static org.bitbucket.dollar.Dollar.$;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class RedisKeyGenerator {
    static String validCharacters = $('0', '9').join() + $('A', 'Z').join();

    public static String generate(int length) {
        return $(validCharacters).shuffle().slice(length).toString();
    }

    public static String mount(String prefix, String key) {
        return prefix + ":" + key;
    }
}
