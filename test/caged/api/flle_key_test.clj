(ns caged.api.flle-key-test
  (:require [clojure.test :refer [deftest testing is]]
            [caged.api.flle-key :as flle-key]))

(deftest test-create-file-key
  (testing "Does not throw an exception"
    (is (not (thrown? Exception (flle-key/create-file-key)))))
  (testing "Returns a FileKey instance"
    (is (instance? com.exceptionfactory.jagged.FileKey (flle-key/create-file-key)))))

(deftest test-write-key-to-file
  (testing "Does not throw an exception"
    (is (not (thrown? Exception (flle-key/write-key-to-file "test/resources/test.key"))))))

(deftest test-create-file-key-from-bytes
  (testing "Does not throw an exception"
    (is (not (thrown? Exception (flle-key/create-file-key-from-bytes (byte-array 32)))))
    (testing "Returns a FileKey instance"
      (is (instance? com.exceptionfactory.jagged.FileKey (flle-key/create-file-key-from-bytes (byte-array 32))))))
  (testing "Throws an exception when the byte array is not 32 bytes long"
    (is (thrown? Exception (flle-key/create-file-key-from-bytes (byte-array 31))))))

(deftest test-get-algorithm
  (testing "Returns the algorithm used to generate the key"
    (is (= "ChaCha20-Poly1305" (flle-key/get-algorithm (flle-key/create-file-key-from-bytes (byte-array 32)))))))

(deftest test-get-format
  (testing "Returns the format of the key"
    (is (= "RAW" (flle-key/get-format (flle-key/create-file-key-from-bytes (byte-array 32)))))))

(deftest test-get-encoded
  (testing "Returns the encoded key"
    (is (instance? String (flle-key/get-encoded (flle-key/create-file-key-from-bytes (byte-array 32)))))))

(deftest test-file-key-eq?
  (testing "Returns true when the keys are equal"
    (let [cached-key (delay (flle-key/create-file-key-from-bytes (byte-array 32)))]
      (is (flle-key/file-key-eq? @cached-key @cached-key))))
  (testing "Returns false when the keys are not equal"
    (is (not (flle-key/file-key-eq? (flle-key/create-file-key-from-bytes (byte-array 32))
                                    (flle-key/create-file-key-from-bytes (byte-array 31)))))))
