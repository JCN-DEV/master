'use strict';

angular.module('stepApp')
    .factory('RisNewVacancySearch', function ($resource) {
        return $resource('api/_search/risNewVacancys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
