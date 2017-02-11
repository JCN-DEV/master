'use strict';

angular.module('stepApp')
    .factory('InstVacancyTempSearch', function ($resource) {
        return $resource('api/_search/instVacancyTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
