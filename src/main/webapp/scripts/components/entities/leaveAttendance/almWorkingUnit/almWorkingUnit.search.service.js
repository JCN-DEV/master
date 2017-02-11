'use strict';

angular.module('stepApp')
    .factory('AlmWorkingUnitSearch', function ($resource) {
        return $resource('api/_search/almWorkingUnits/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
