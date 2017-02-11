'use strict';

angular.module('stepApp')
    .factory('PfmsRegisterSearch', function ($resource) {
        return $resource('api/_search/pfmsRegisters/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
