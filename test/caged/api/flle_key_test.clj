(ns caged.api.flle-key-test
  (:require [caged.api.file-key :as file-key])
  #_{:clj-kondo/ignore [:use]}
  (:use clojure.test))

(defmethod assert-expr 'not-thrown? [msg form]
  ;; (is (not-thrown? c expr))
  ;; Asserts that evaluating expr does not throws an exception of class c.
  ;; Returns the exception thrown.
  (let [klass (second form)
        body (nthnext form 2)]
    `(try ~@body
          (do-report {:type :pass, :message ~msg,
                      :expected '~form, :actual nil})
          (catch ~klass e#
            (do-report {:type :fail, :message ~msg,
                        :expected '~form, :actual e#})
            e#))))

(deftest test-create-file-key
  (testing "Does not throw an exception"
    (is (not-thrown? Exception (file-key/create-file-key))))
  (testing "Returns a FileKey instance"
    (is (instance? com.exceptionfactory.jagged.FileKey (file-key/create-file-key)))))

(deftest test-write-key-to-file
  (testing "Does not throw an exception"
    (is (not-thrown? Exception (file-key/write-key-to-file "test/resources/test.key")))))

(deftest test-create-file-key-from-bytes
  (testing "Does not throw an exception"
    (is (not-thrown? Exception (file-key/create-file-key-from-bytes (byte-array 32))))
    (testing "Returns a FileKey instance"
      (is (instance? com.exceptionfactory.jagged.FileKey (file-key/create-file-key-from-bytes (byte-array 32))))))
  (testing "Throws an exception when the byte array is not 32 bytes long"
    (is (thrown? Exception (file-key/create-file-key-from-bytes (byte-array 31))))))

(deftest test-get-algorithm
  (testing "Returns the algorithm used to generate the key"
    (is (= "ChaCha20-Poly1305" (file-key/get-algorithm (file-key/create-file-key-from-bytes (byte-array 32)))))))

(deftest test-get-format
  (testing "Returns the format of the key"
    (is (= "RAW" (file-key/get-format (file-key/create-file-key-from-bytes (byte-array 32)))))))

(deftest test-get-encoded
  (testing "Returns the encoded key"
    (is (instance? String (file-key/get-encoded (file-key/create-file-key-from-bytes (byte-array 32)))))))

(deftest test-file-key-eq?
  (testing "Returns true when the keys are equal"
    (let [cached-key (delay (file-key/create-file-key-from-bytes (byte-array 32)))]
      (is (file-key/file-key-eq? @cached-key @cached-key))))
  (testing "Returns false when the keys are not equal"
    (is (not (file-key/file-key-eq? (file-key/create-file-key-from-bytes (byte-array 32))
                                    (file-key/create-file-key-from-bytes (byte-array 31)))))))
