class CreateCities < ActiveRecord::Migration
  def change
    create_table :cities do |t|
      t.text :name
      t.text :state

      t.timestamps
    end

    rio_claro = City.new
    rio_claro.name = 'Rio Claro'
    rio_claro.state = 'SP'
    rio_claro.save!

    campinas = City.new
    campinas.name = 'Campinas'
    campinas.state = 'SP'
    campinas.save!

    recife = City.new
    recife.name = 'Recife'
    recife.state = 'PE'
    recife.save!

    olinda = City.new
    olinda.name = 'Olinda'
    olinda.state = 'PE'
    olinda.save!
  end
end
