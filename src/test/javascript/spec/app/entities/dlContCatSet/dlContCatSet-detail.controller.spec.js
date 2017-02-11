'use strict';

describe('DlContCatSet Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlContCatSet, MockDlContTypeSet;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlContCatSet = jasmine.createSpy('MockDlContCatSet');
        MockDlContTypeSet = jasmine.createSpy('MockDlContTypeSet');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlContCatSet': MockDlContCatSet,
            'DlContTypeSet': MockDlContTypeSet
        };
        createController = function() {
            $injector.get('$controller')("DlContCatSetDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlContCatSetUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
