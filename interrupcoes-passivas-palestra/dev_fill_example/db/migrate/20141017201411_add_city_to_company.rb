class AddCityToCompany < ActiveRecord::Migration
  def change
    add_column :companies, :city, :text
  end
end
