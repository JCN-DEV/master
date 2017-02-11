'use strict';

describe('InstEmplDesignation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmplDesignation;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmplDesignation = jasmine.createSpy('MockInstEmplDesignation');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmplDesignation': MockInstEmplDesignation
        };
        createController = function() {
            $injector.get('$controller')("InstEmplDesignationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmplDesignationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
