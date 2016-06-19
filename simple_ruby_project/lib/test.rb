require 'time'

class Test

  class Call
    def initialize(number, cost)
      @number = number
      @cost = cost
    end

    def cost
      @cost
    end

    def number
      @number
    end
  end

  def solution(s)
    sum = 0
    calls_hash = {}

    calls = s.split("\n")
    calls.each do |call|
      duration = call.split(',')[0]
      number = call.split(',')[1]

      total_for_number = calls_hash[number] ? calls_hash[number] : 0
      total_for_number = total_for_number + cost(duration)

      calls_hash[number] = total_for_number
    end

    calls_array = calls_hash.map do |number, cost|
      Call.new(number, cost)
    end

    calls_array.sort! do |a, b|
        b.cost <=> a.cost
    end

    puts "before=#{calls_array}"

    calls_array.pop

    puts "after=#{calls_array}"

    result = calls_array.reduce {|a, b| a.cost + b.cost}
    result.cost
  end

  def cost(call)
      t = Time.parse(call)
      seconds = (t.hour * 3600) + (t.min * 60) + t.sec

      if seconds < (5 * 60)
        seconds * 3
      else
        started_hours = (seconds / 60.0).ceil
        started_hours * 150
      end
  end
end
