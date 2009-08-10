;;  Copyright (c) Chris Turner. All rights reserved.  The use and
;;  distribution terms for this software are covered by the Eclipse Public
;;  License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) which can
;;  be found in the file epl-v10.html at the root of this distribution.  By
;;  using this software in any fashion, you are agreeing to be bound by the
;;  terms of this license.  You must not remove this notice, or any other,
;;  from this software.

(ns clojure.contrib.test-contrib.test-spec
  (:use clojure.test
	[clojure.contrib.spec :only (describe it)]))

(defn- example-fn [arg1 arg2] (str arg1 arg2))

(deftest test-describe-enhances-metadata
  (is (= (do
           (describe example-fn)
           (-> #'example-fn meta :spec))
         ""))
  (is (= (do
           (describe example-fn
             (it "is composed of two strings"
                 (= (example-fn "a" "b") "ab")))
           (-> #'example-fn meta :spec))
         "- is composed of two strings")))

(deftest test-it-acts-like-test-is
  (is (= (it "does not need a test definition")
         "- does not need a test definition (Pending)"))
  (is (= (binding [clojure.test/report
                   (fn [x]
                     (do
                       (assert (= :pass (:type x)))
                       (assert (= "should be equal" (:message x)))))]
           (it "should be equal" (= 1 1)))
         "- should be equal"))
  (is (= (binding [clojure.test/report
                   (fn [x]
                     (do
                       (assert (= :error (:type x)))
                       (assert (= "should not be equal" (:message x)))))]
           (it "should not be equal" (= 1 2)))
         "- should not be equal")))
