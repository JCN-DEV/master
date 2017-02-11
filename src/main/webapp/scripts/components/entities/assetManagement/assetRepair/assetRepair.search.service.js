'use strict';

angular.module('stepApp')
    .factory('AssetRepairSearch', function ($resource) {
        return $resource('api/_search/assetRepairs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
