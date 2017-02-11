'use strict';

angular.module('stepApp')
    .factory('InstEmplBankInfoSearch', function ($resource) {
        return $resource('api/_search/instEmplBankInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
