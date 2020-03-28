import { Component, Input } from '@angular/core';

import { FilmService } from '../services/film/film.service';
import { FilmDTO } from '@api/generated/film/models/film-dto';
import { FilmSelectionService } from '../services/film-selection/film-selection.service';
import { InventoryService } from '../services/inventory/inventory.service';
import { InventoryDTO } from '@api/generated/store/models/inventory-dto';

@Component({
  selector: 'app-film',
  templateUrl: './film.component.html',
  styleUrls: ['./film.component.css']
})
export class FilmComponent {

  @Input() film: FilmDTO;

  private inventories: Array<InventoryDTO>;

  constructor(
    private filmSelectionService: FilmSelectionService,
    private inventoryService: InventoryService
  ) {}

  isFilmSelected(): boolean {
    let selection: Array<number> = this.filmSelectionService.getSelectedFilmIds();
    if (selection == null) return false

    return selection.indexOf(this.film.id) > -1
  }

  doClick() {
    if (this.isFilmSelected()) {
      this.deselectFilm();
    } else {
      this.selectFilm();
    }
  }

  selectFilm() {
    this.filmSelectionService.addFilmToSelection(this.film.id);
  }

  deselectFilm() {
    this.filmSelectionService.removeFilmFromSelection(this.film.id);
  }

  isFilmInStock(): boolean {
    if (this.inventories == null) return false;
    return this.inventories.length > 0;
  }

  getCurrentInventories() {
    return this.inventoryService.getInventoriesByFilmId(this.film.id)
      .subscribe(result => this.inventories = result);
  }
}
