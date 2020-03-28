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
    let selection: Array<string> = this.filmSelectionService.getSelectedFilmIds();
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
    if (!this.film.inventories) return false;

    if (this.film.inventories.length == 0) return false;

    let inStock: boolean = false;
    for (let inventory of this.film.inventories) {
      if (inventory.quantity > 0) {
        inStock = true;
        break;
      }
    }

    return inStock;
  }
}
