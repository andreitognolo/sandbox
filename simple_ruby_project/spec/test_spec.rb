require_relative 'spec_helper'

require 'test'

describe Test do

  describe '#cost' do
    it 'less than 5 minutes, 3 cents per second' do
      t = Test.new

      assert_equal 201, t.cost('00:01:07')
    end

    it 'exactly 5 minutes, 150 cents for every started minute' do
      t = Test.new

      assert_equal 750, t.cost('00:05:00')
    end

    it 'more than 5 minutes' do
      t = Test.new

      assert_equal 900, t.cost('00:05:01')
    end
  end

  describe '#test' do
    it 'should sum' do
      t = Test.new

      s = ['00:01:07,400-234-090', '00:05:01,701-080-080'].join("\n")

      assert_equal 201, t.solution(s)
    end

    it 'should not consider the highest' do
      t = Test.new

      s = ['00:01:07,400-234-090', '00:05:01,701-080-080', '00:05:00,400-234-090'].join("\n")

      assert_equal 900, t.solution(s)
    end

    it 'should know how to solve a draw' do

    end
  end
end
