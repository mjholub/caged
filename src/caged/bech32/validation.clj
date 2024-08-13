(ns caged.bech32.validation)

(def b32-regex #"^(bc1)[a-z0-9]{39}$")

(defn validate
  "Validates a Bech32-encoded string.
 Returns a boolean."
  [b32-string]
  #_{:clj-kondo/ignore [:not-empty?]}
  (not (empty? (re-matches b32-regex b32-string))))
