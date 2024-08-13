(ns caged.api.file-key
  (:import (com.exceptionfactory.jagged FileKey))
  (:require [clojure.java.io :as io]))

(defn create-file-key
  "Creates a new key file"
  []
  (try
    (FileKey.)
    (catch Exception e
      (ex-info "Failed to create a new key file" {:exception e}))))

(defn write-key-to-file
  "Creates a new key file and writes it to the specified file"
  [path]
  (try
    (let [file-key (create-file-key)]
      (.writeToFile file-key (io/file path)))
    (catch Exception e
      (ex-info "Failed to write key to file" {:exception e}))))

(defn create-file-key-from-bytes
  "Creates a new key file from a byte array"
  [input-bytes]
  (try
    (if (not= 16 (count input-bytes))
      (ex-info "Incorrect input length. Must be 16 bytes." {:length (count input-bytes)})
      (FileKey. input-bytes))
    (catch Exception e
      (ex-info "Failed to create a new key file from byte array" {:exception e}))))

(defn create-random
  "Explicitly uses clojure functions to generate the input 
  byte array passed to create-file-key-from-bytes"
  []
  (let [randbarr (byte-array (vec (repeatedly 16 #(rand-int 16))))]
    (create-file-key-from-bytes randbarr)))

(defn get-algorithm
  "Returns the algorithm used to generate the key.
  Age supported algorithms are:
  - ChaCha20-Poly1305
  - HmacSHA256
  - PBKDF2WithHmacSHA256
  - RSA/ECB/OAEPPadding
  - X25519"
  [file-key]
  (.getAlgorithm file-key))

(defn get-format
  "Returns the format of the key file. This is the format of the key file, not the key itself."
  [file-key]
  (.getFormat file-key))

(defn get-encoded
  "Returns the encoded bytes of the key file."
  [file-key]
  (try
    (.getEncoded file-key)
    (catch Exception e
      (ex-info "Error getting encoded key file"
               {:cause (.getMessage e)
                :input file-key}))))

(defn destroy
  "Destroys the key file."
  [file-key]
  (.destroy file-key))

(defn is-destroyed? [file-key]
  "Returns true if the key file has been destroyed.
  Destroyed key files cannot be used to perform cryptographic operations."
  (.isDestroyed file-key))

(defn file-key-eq?
  "Compares two key files for equality."
  [file-key1 file-key2]
  (.equals file-key1 file-key2))

(defn hash-code
  "Returns the hash code of the key file."
  [file-key]
  (.hashCode file-key))
