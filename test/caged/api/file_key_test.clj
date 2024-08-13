(ns caged.api.file-key-test
  (:require
   [caged.api.file-key :as file-key]
   [clojure.test :refer [assert-expr deftest do-report is testing]])
  (:import
   [clojure.lang ExceptionInfo]))

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
    (is #_{:clj-kondo/ignore [:unresolved-symbol]}
     (not-thrown? Exception (file-key/create-file-key))))
  (testing "Returns a FileKey instance"
    (is (instance? com.exceptionfactory.jagged.FileKey (file-key/create-file-key)))))

(deftest test-write-key-to-file
  (testing "Does not throw an exception"
    (is #_{:clj-kondo/ignore [:unresolved-symbol]}
     (not-thrown? Exception (file-key/write-key-to-file "test/resources/test.key")))))

(deftest test-create-file-key-from-bytes
  (testing "Does not throw an exception"
    (is #_{:clj-kondo/ignore [:unresolved-symbol]}
     (not-thrown? Exception (file-key/create-file-key-from-bytes (byte-array 16)))))
  (testing "Returns a FileKey instance"
    (is (instance?
         com.exceptionfactory.jagged.FileKey
         (file-key/create-file-key-from-bytes
          (byte-array (vec (repeatedly 16 #(rand-int 16)))))))))
(testing "Returns nil, calling ex-info when the byte array is not 16 bytes long"
  (is (instance? ExceptionInfo (file-key/create-file-key-from-bytes (byte-array 31)))))

(deftest test-get-algorithm
  (testing "Returns the algorithm used to generate the key"
    (is (= "age-encryption.org" (file-key/get-algorithm (file-key/create-random))))))

(deftest test-get-format
  (testing "Returns the format of the key"
    (is (= "RAW" (file-key/get-format (file-key/create-file-key-from-bytes (byte-array 16)))))))

(deftest test-get-encoded
  (testing "Returns the encoded key"
    (let [input-vec (vec (repeatedly 16 #(rand-int 16)))]
      (is (= input-vec (vec (file-key/get-encoded (file-key/create-file-key-from-bytes
                                                   (byte-array input-vec)))))))))

(deftest test-file-key-eq?
  (testing "Returns true when the keys are equal"
    (let [cached-key (delay (file-key/create-file-key-from-bytes (byte-array 16)))]
      (is (file-key/file-key-eq? @cached-key @cached-key))))
  (testing "Returns false when the keys are not equal"
    (is (not (file-key/file-key-eq? (file-key/create-random)
                                    (file-key/create-random))))))
