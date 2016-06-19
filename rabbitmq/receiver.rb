require 'bunny'

conn = Bunny.new
conn.start

ch = conn.create_channel
q = ch.queue('hello')

puts 'Waiting for messsages'
q.subscribe(:block => true) do |delivery_info, properties, body|
  puts "Received #{body}"

  delivery_info.consumer.cancel
end

conn.close
