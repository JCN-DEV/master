'use strict';

angular.module('stepApp')
    .factory('AssetAccuisitionSetupSearch', function ($resource) {
        return $resource('api/_search/assetAccuisitionSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
