'use strict';

angular.module('stepApp')
    .factory('DlExistBookInfoSearch', function ($resource) {
        return $resource('api/_search/dlExistBookInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
