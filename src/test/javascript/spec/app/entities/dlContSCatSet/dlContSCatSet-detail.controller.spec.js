'use strict';

describe('DlContSCatSet Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlContSCatSet, MockDlContTypeSet, MockDlContCatSet;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlContSCatSet = jasmine.createSpy('MockDlContSCatSet');
        MockDlContTypeSet = jasmine.createSpy('MockDlContTypeSet');
        MockDlContCatSet = jasmine.createSpy('MockDlContCatSet');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlContSCatSet': MockDlContSCatSet,
            'DlContTypeSet': MockDlContTypeSet,
            'DlContCatSet': MockDlContCatSet
        };
        createController = function() {
            $injector.get('$controller')("DlContSCatSetDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlContSCatSetUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
