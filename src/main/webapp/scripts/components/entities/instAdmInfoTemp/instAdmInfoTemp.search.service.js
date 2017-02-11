'use strict';

angular.module('stepApp')
    .factory('InstAdmInfoTempSearch', function ($resource) {
        return $resource('api/_search/instAdmInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
