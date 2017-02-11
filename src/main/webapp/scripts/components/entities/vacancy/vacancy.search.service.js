'use strict';

angular.module('stepApp')
    .factory('VacancySearch', function ($resource) {
        return $resource('api/_search/vacancys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
