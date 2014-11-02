class AddStateToCompany < ActiveRecord::Migration
  def change
    add_column :companies, :state, :text
  end
end
