require_relative 'spec_helper'

require 'test'

describe Test do

  describe '#tes' do
    it 'should return test' do
      t = Test.new
      assert_equal 'test', t.test
    end
  end
end
