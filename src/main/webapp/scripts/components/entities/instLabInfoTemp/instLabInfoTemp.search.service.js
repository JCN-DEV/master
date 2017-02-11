'use strict';

angular.module('stepApp')
    .factory('InstLabInfoTempSearch', function ($resource) {
        return $resource('api/_search/instLabInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
