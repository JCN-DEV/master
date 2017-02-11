'use strict';

angular.module('stepApp')
    .factory('AlmLeavGrpTypeMapSearch', function ($resource) {
        return $resource('api/_search/almLeavGrpTypeMaps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
