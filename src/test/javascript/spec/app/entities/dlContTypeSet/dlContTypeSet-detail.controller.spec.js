'use strict';

describe('DlContTypeSet Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlContTypeSet;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlContTypeSet = jasmine.createSpy('MockDlContTypeSet');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlContTypeSet': MockDlContTypeSet
        };
        createController = function() {
            $injector.get('$controller')("DlContTypeSetDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlContTypeSetUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
