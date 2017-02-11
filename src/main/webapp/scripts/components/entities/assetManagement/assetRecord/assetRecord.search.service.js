'use strict';

angular.module('stepApp')
    .factory('AssetRecordSearch', function ($resource) {
        return $resource('api/_search/assetRecords/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
